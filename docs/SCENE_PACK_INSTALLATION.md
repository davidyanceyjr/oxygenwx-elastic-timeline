# Scene-pack installation

OX-57 installs one locally supplied OX-30 artifact after OX-91 has accepted
its complete manifest and referenced resources. Installation writes the
accepted asset bytes and app-owned catalog registration into private app
storage. It does not activate a scene, choose an installed theme, change
forecast facts, or change selected time.

## Input and ownership

The current OX-57 entry point is a developer-only `File` handoff. It accepts a
device-local artifact path plus an app-managed display name and opaque content
or release version. It adds no production picker or storefront. OX-91 validates
the archive and supplies the typed manifest, exact referenced asset bytes, and
content identity. OX-57 does not reopen or copy the ZIP after validation.

The manifest supplies `packId`; the app supplies the display name and version.
The version is not the manifest version, renderer-policy version, content
identity, or asset digest. OX-57 carries OX-54's returned content identity
unchanged in its private install record. OX-91's acceptance establishes the
`COMPATIBLE` registration disposition used by the OX-31 catalog composer.
Catalog composition remains read-only. OX-55 owns renderer loading and scene
activation, and OX-32 owns in-session theme switching.

## Staging and commit

Install data lives below the app-private `files/installed-scene-packs/`
directory, partitioned by `packId`. A candidate is written beneath that pack's
private `staging` directory. The generation contains the canonical referenced
asset path and one bounded internal record containing the typed manifest,
content identity, app-owned display name and version, and compatibility
disposition. Unused ZIP directory entries are not materialized.

Before writing, OX-57 computes the resource, record, filesystem-node, working
data, candidate, and replacement totals. It rejects a candidate that exceeds
any limit. Resource paths are resolved canonically beneath the staging root;
escaping paths are rejected. OX-57 flushes and synchronizes the staged resource
and record, checks the resource digest and record round trip, and checks the
materialized node count before commit.

After verification, OX-57 atomically renames the staged directory to a unique
generation directory. It then writes and synchronizes a bounded pending
current-generation pointer and atomically replaces the pack's
`current-generation` pointer. That pointer is the sole selector for the
committed generation and registration; catalog results are not discovered by
scanning staging or generation directories. If the filesystem cannot perform
the required atomic rename, installation fails instead of using a non-atomic
fallback.

For same-pack replacement, the old generation remains current until the new
pointer is published. The superseded generation is removed after commit. A
failure before pointer publication leaves the prior generation selected; an
in-process cleanup failure is reported and does not change that pointer.

The current OX-57 catalog query uses registrations known to the live installer
instance. Cold-relaunch enumeration and restoration are assigned to OX-58.
OX-57 does not claim recovery from process death or power loss during commit.

## OX-57 install budgets

All limits are inclusive and per target pack or generation as indicated.
They are separate from OX-91 archive/resource validation and OX-55 renderer
load limits.

| Budget | Limit | Accounting |
| --- | ---: | --- |
| Materialized filesystem nodes | 64 per generation | Count the generation root, required asset ancestor directories, asset file, and install-record file. |
| Accepted resource bytes | 1,572,864 | OX-91's aggregate accepted-resource ceiling. |
| Install-record bytes | 32,768 | Maximum serialized private registration and manifest record. |
| Expanded generation bytes | 1,605,632 | Resource bytes plus install-record bytes. |
| Current-generation pointer bytes | 16,384 per pointer | Bounds each current or pending pointer copy. |
| Candidate plus commit bookkeeping | 1,638,400 | One maximum candidate generation plus two pointer copies. |
| Same-pack replacement peak | 3,244,032 | One maximum old generation, one maximum candidate generation, and two pointer copies. |
| Package-specific working data | 1,622,016 | Accepted resource bytes plus install record plus one 16,384-byte write buffer. |

The resource total is capped at OX-91's 1,572,864-byte aggregate maximum. The
node and expanded-byte limits apply to materialized output, not ZIP transport
bytes. OX-91 continues to own its entry count, central-directory, manifest,
path, media, and archive-resource checks. OX-55's decoded-image and candidate
load budget remains independent.

## Failure outcomes

| Outcome | Result |
| --- | --- |
| OX-91 rejection or non-canonical install input | Reject before publishing a generation; no candidate registration becomes visible. |
| OX-57 limit exceeded | Reject before commit; the existing same-pack generation remains current. |
| Staging write, verification, or atomic-move failure | Report a write failure; keep the previous current pointer. Remove candidate files when in-process cleanup succeeds. |
| Deterministic debug interruption after the first staged resource write | Report `INTERRUPTED`; clean the uncommitted stage when possible and keep the prior generation and registration current. This hook does not simulate process death. |
| Successful commit followed by superseded-generation cleanup failure | Keep the new generation committed and report that old-generation cleanup failed. |

Import changes app-owned pack storage and registration only. OX-57 device proof
records the same non-empty weather facts and selected instant before and after
both a successful install and a deterministic interrupted replacement in
[cycle 166 evidence](../.codex/test-artifacts/166-ox57-atomic-pack-install/README.md).
