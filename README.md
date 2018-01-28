# SuncorpTest

This is a simple program written for the Suncorp / Sapient Razorfish coding test. It's primarily written in Kotlin, but I've included a little Java that was copied over from another project.

## Design Decisions

The specification required the app not trigger off a second download if the device was rotated. This ruled out an `AsyncTask`, as these will either need to be passed when the `Activity` is destroyed, or recreated, which will entail restarting the download.

The sensible solution would have been to use a `Service` backed up by a `Thread` to keep the download off the main UI thread. But the spec mentioned using a database, which would have added a layer of complexity.

RxJava might have been an option, but I've taken a brief look at the description on the website and didn't want to learn a new API and a new language at the same time.

I decided to use a `SyncAdapter` backed up by a `ContentProvider`. This has been a rod to beat my own back with.

For a full-blown app, I'd argue they're the way to go, as you can leverage a lot of code to schedule updates that are in the API. But they're a significant amount of work to put together.

It's possible that there are better solutions out there. Server comms are normally one of the first things that are done on a project, and typically written once. As a contractor I often join a team a few weeks in, so the work's completed by then.

## Current Status

### Synchronisation

The `SyncAdapter` isn't being triggered. I'm not sure why, but I think that there's a problem with the configuration. Having spent several hours on Sunday looking at it, I'm still none the wiser.

My guess is that part of the configuration is wrong. The `SyncAdapter` documentation on the Android developer's site sometimes is neither particularly clear, nor that complete.

That said, I haven't worked with that API for several years, so I might be missing something.

### StatememtAsyncTask

This is a placeholder for the synchronisation code. It's there to test that the download code is working.

If I get the `SyncAdapter` working, it'll be pulled out.

### UI

This is currently very basic. It doesn't even meet the requirements of the test. I'm missing a header row (saying what each column does), and a Totals row.

### Rotation

The download is restarted when the device is rotated. If I get the `SyncAdapter` working, it'll be easy to fix.

## Criticisms

The trouble with this project is that it's taken a lot longer than I thought or was estimated. I was told that it'd take three to five hours. I'm probably pushing fifteen at this point, and I'm still not finished.

I think that this is for several reasons:

1. I didn't have much existing code that I could reuse for this project, so everything had to be written from scratch.
2. I was requested to write it in Kotlin, which is a language I haven't used before, and was largely unfamiliar with. I still don't feel fluent with it, and I still need to figure out some of its conventions. But it meant that I regularly had to stop to look up answers to the basic syntax.
3. I've not coded much recently, so I'm a bit rusty.
